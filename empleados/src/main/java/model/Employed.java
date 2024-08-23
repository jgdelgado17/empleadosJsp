package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employed {
    private int id;
    private String firstName;
    private String lastName;
    private Date entryDate;
    private double salary;
}
