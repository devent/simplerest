/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of simplerest-oanda-rest.
 *
 * simplerest-oanda-rest is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * simplerest-oanda-rest is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with simplerest-oanda-rest. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.simplerest.oanda.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.anrisoftware.simplerest.core.ParseResponse;
import com.anrisoftware.simplerest.core.SimpleRestException;
import com.anrisoftware.simplerest.oanda.api.BidaskCandle;
import com.anrisoftware.simplerest.oanda.api.Candle;
import com.anrisoftware.simplerest.oanda.api.Granularity;
import com.anrisoftware.simplerest.oanda.api.Instrument;
import com.anrisoftware.simplerest.oanda.api.InstrumentHistory;
import com.anrisoftware.simplerest.oanda.api.OandaAccount;
import com.anrisoftware.simplerest.oanda.api.OandaErrorException;
import com.anrisoftware.simplerest.oanda.rest.SimpleRequestWorker.SimpleRequestWorkerFactory;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

/**
 * Retrieves the price history of the tradeable instrument.
 *
 * @see BidaskCandle
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 0.3
 */
class AbstractInstrumentHistory<T extends Candle> implements InstrumentHistory {

    private static final String GRANULARITY_PARAM = "granularity";

    private static final String END_PARAM = "end";

    private static final String START_PARAM = "start";

    private static final String COUNT_PARAM = "count";

    private static final String CANDLE_FORMAT_PARAM = "candleFormat";

    private static final String INSTRUMENT_PARAM = "instrument";

    private static final String ACCOUNT_ID_PARAM = "accountId";

    private static final String ACCOUNT_FIELD = "account";

    private static final String CANDLES_NAME = "candles";

    private final OandaAccount account;

    private final Instrument instrument;

    private final Class<? extends T> candleType;

    private final String candleFormat;

    @Inject
    private AbstractInstrumentHistoryLogger log;

    @Inject
    private OandaPropertiesProvider propertiesProvider;

    @Inject
    private SimpleRequestWorkerFactory simpleRequestWorkerFactory;

    private Collection<T> container;

    private Granularity granularity;

    private int count;

    private DateTime startDate;

    private DateTime endDate;

    private CloseableHttpClient httpClient;

    protected AbstractInstrumentHistory(OandaAccount account,
            Instrument instrument, String candleFormat,
            Collection<T> container, Class<? extends T> candleType,
            CloseableHttpClient httpClient) {
        this.account = account;
        this.instrument = instrument;
        this.count = 500;
        this.container = container;
        this.candleType = candleType;
        this.candleFormat = candleFormat;
        this.httpClient = httpClient;
    }

    @Override
    public OandaAccount getAccount() {
        return account;
    }

    @Override
    public Instrument getInstrument() {
        return instrument;
    }

    public void setHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void setGranularity(Granularity granularity) {
        this.granularity = granularity;
    }

    @Override
    public Granularity getGranularity() {
        return granularity;
    }

    @Override
    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    @Override
    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public Collection<? extends Candle> getCandles() {
        return container;
    }

    @Override
    public InstrumentHistory call() throws OandaErrorException {
        retrieveHistory();
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(ACCOUNT_FIELD,
                account.toString()).toString();
    }

    private void retrieveHistory() throws OandaErrorException {
        URI requestUri = getRequestURI();
        SimpleRequestWorker requestWorker = simpleRequestWorkerFactory.create(
                this, account, requestUri, new CandlesParseResponse(),
                httpClient);
        try {
            @SuppressWarnings("unchecked")
            Collection<T> candles = requestWorker.retrieveData();
            this.container = candles;
        } catch (SimpleRestException e) {
            throw new ErrorRetrieveOandaRestDataException(e);
        }
    }

    private URI getRequestURI() throws BadRequestURI {
        try {
            return getRequestURI0();
        } catch (URISyntaxException e) {
            throw new BadRequestURI(e);
        }
    }

    private URI getRequestURI0() throws URISyntaxException {
        URIBuilder builder = new URIBuilder(
                propertiesProvider.getOandaCandlesURI());
        builder.setParameter(ACCOUNT_ID_PARAM, account.getAccount());
        builder.setParameter(CANDLE_FORMAT_PARAM, candleFormat);
        builder.addParameter(INSTRUMENT_PARAM, instrument.getName());
        if (granularity != null) {
            builder.addParameter(GRANULARITY_PARAM, granularity.toString());
        }
        if (endDate == null) {
            builder.addParameter(COUNT_PARAM, Integer.toString(count));
        }
        if (startDate != null) {
            builder.addParameter(START_PARAM, toRfc3339Date(startDate));
        }
        if (endDate != null) {
            builder.addParameter(END_PARAM, toRfc3339Date(endDate));
        }
        return builder.build();
    }

    private String toRfc3339Date(DateTime date) {
        DateTime dt = new DateTime(date, DateTimeZone.UTC);
        DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
        return fmt.print(dt);
    }

    /**
     * Parses a named list from the JSON response.
     *
     * @author Erwin Müller, erwin.mueller@deventm.de
     * @since 1.0
     */
    class CandlesParseResponse implements ParseResponse<Collection<?>> {

        @Override
        public Collection<T> parse(HttpResponse response) throws IOException {
            ObjectMapper mapper = createParserMapper();
            JsonParser parser = createParser(mapper, response.getEntity());
            return parseResponseList(mapper, parser);
        }

        private ObjectMapper createParserMapper() {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JodaModule());
            return mapper;
        }

        private Collection<T> parseResponseList(ObjectMapper mapper,
                JsonParser parser) throws IOException, JsonParseException,
                JsonMappingException {
            return parseResponseList0(mapper, parser);
        }

    }

    private Collection<T> parseResponseList0(ObjectMapper mapper,
            JsonParser parser) throws IOException, JsonParseException,
            JsonMappingException {
        TreeNode tree = parser.readValueAsTree();
        TreeNode candlesNode = tree.get(CANDLES_NAME);
        int size = candlesNode.size();
        TreeNode node;
        for (int i = 0; i < size; i++) {
            node = candlesNode.get(i);
            T obj = mapper.readValue(node.traverse(), candleType);
            log.parsedInstrumentHistory(obj);
            container.add(obj);
        }
        return container;
    }

    private JsonParser createParser(ObjectMapper mapper, HttpEntity entity)
            throws IOException, JsonParseException, JsonMappingException {
        JsonFactory factory = mapper.getFactory();
        JsonParser parser = factory.createParser(entity.getContent());
        return parser;
    }

}
