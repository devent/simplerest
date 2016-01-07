package com.anrisoftware.simplerest.ocs;

import java.util.List;

import com.anrisoftware.simplerest.core.Message;

/**
 * OCS response message.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public interface OcsMessage extends Message {

    OcsMeta getMeta();

    List<? extends OcsData> getData();
}
