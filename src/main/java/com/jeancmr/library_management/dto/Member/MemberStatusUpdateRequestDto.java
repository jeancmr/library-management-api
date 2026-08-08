package com.jeancmr.library_management.dto.Member;

import com.jeancmr.library_management.enums.MemberStatus;

public class MemberStatusUpdateRequestDto {
    private MemberStatus status;
    private int borrowLimit;
}
