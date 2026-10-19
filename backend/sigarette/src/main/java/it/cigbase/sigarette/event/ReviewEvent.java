package it.cigbase.sigarette.event;

import it.cigbase.sigarette.util.ReviewEventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewEvent implements Serializable {
    private Long idCigarette;
    private Float rating;
    private ReviewEventType reviewEventType;
}
