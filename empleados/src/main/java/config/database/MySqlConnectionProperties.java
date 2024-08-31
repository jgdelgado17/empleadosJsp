package config.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MySqlConnectionProperties {

    private String database;
    private String username;
    private String password;
    private String host;
    private String port;
    private String url;
    private String driver;
}
