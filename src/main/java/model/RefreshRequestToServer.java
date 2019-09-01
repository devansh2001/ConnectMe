package model;

import lombok.Data;

import java.io.Serializable;

@Data
public class RefreshRequestToServer implements Serializable {
    String username;
}
