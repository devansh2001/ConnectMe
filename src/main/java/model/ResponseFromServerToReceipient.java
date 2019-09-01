package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ResponseFromServerToReceipient implements Serializable {
    String sender;
    String data;
    String timestamp;
}
