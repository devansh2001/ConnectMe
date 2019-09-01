package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;
@Data
@AllArgsConstructor
public class MessageToServer implements Serializable  {
    private String data;
    private String from;
    private String to;
}
