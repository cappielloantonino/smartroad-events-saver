package it.almaviva.smartroadeventssaver.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.almaviva.etsi.Enum;
import it.almaviva.etsi.Etsi;
import it.almaviva.etsi.denm.Denm;
import it.almaviva.etsi.ivim.Ivim;

import java.text.ParseException;
import java.util.IllegalFormatException;

public class EtsiParser {

    public static Etsi jsonToEtsi(String jsonMessage, Enum.MessageType messageType) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Etsi etsi = null;

        switch (messageType) {
            case DENM: etsi = objectMapper.readValue(jsonMessage, Denm.class); break;
            case IVIM: etsi = objectMapper.readValue(jsonMessage, Ivim.class); break;
            default: break;
        }

        if(etsi.getMessageType() == null) {
            throw new Exception("EtsiParser[jsonToEtsi] - non è possibile stabilire il tipo di messaggio ricevuto, " +
                    "potrebbe trattarsi di un oggetto vuoto - " + etsi.toString());
        }

        return etsi;
    }

    public static String etsiToJson(Etsi etsi) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(etsi);
    }
}
