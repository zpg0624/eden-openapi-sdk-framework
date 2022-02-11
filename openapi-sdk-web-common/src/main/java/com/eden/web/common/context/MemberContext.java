package com.eden.web.common.context;


import com.eden.core.entity.MemberEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MemberContext {

    ThreadLocal<MemberEntity> currentMember = new ThreadLocal<>();

    public MemberEntity current() {
        return currentMember.get();
    }

    public void setCurrent(MemberEntity member) {
        currentMember.set(member);
    }

    public void reset() {
        currentMember.remove();
    }
}
