package it.almaviva.smartroadeventssaver.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.almaviva.etsi.Enum;
import it.almaviva.etsi.Etsi;
import it.almaviva.etsi.denm.Denm;
import it.almaviva.etsi.ivim.Ivim;

public class EtsiMapper {

    public static Etsi convertToEtsi(String jsonMessage, Enum.MessageType messageType) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Etsi etsi = null;

        switch (messageType) {
            case DENM: etsi = objectMapper.readValue(jsonMessage, Denm.class); break;
            case IVIM: etsi = objectMapper.readValue(jsonMessage, Ivim.class); break;
            default: break;
        }

        return etsi;
    }

    public static String convertToJson(Etsi etsi) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(etsi);
    }
}
