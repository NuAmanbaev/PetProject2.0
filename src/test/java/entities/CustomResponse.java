package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.junit.Ignore;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomResponse {
    private int category_id;
    private String category_title;
    private String email;
    private  int seller_id;
    List<CustomResponse> responses;
    private String seller_name;

}

