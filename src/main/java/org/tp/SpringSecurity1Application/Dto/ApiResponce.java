package org.tp.SpringSecurity1Application.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponce {
    private HttpStatus status;
    private String message;
    private Object data;
    private Object error;

}
