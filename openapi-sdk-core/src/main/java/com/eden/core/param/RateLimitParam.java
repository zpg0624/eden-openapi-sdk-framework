package com.eden.core.param;

import com.eden.core.annotations.Limit;
import com.eden.core.enums.LimitType;
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

    private LimitType limitType;
}
