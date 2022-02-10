package com.eden.core.param;

import com.eden.core.annotations.Limit;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor(staticName = "of")
@RequiredArgsConstructor(staticName = "ofAnnotation")
public class RateLimitParam {

    private String defaultKey;

    @NonNull
    private Limit limitAnnotation;

}
