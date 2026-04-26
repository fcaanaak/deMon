package org.modsec.jakarta.wsserver;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;

public class DeviceReportDecoder implements Decoder.Text<JsonObject>{

    @Override
    public JsonObject decode(String s) throws DecodeException {
        try (JsonReader reader = Json.createReader(new StringReader(s))) {
            return reader.readObject();
        } catch (Exception e) {
            JsonObject error = Json.createObjectBuilder()
                    .add("error", e.getMessage())
                    .build();
            return error;
        }
    }

    @Override
    public boolean willDecode(String s) {
        try (JsonReader reader = Json.createReader(new StringReader(s))) {
            reader.readObject();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}