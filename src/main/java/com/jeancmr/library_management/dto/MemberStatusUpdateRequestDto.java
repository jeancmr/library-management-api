package com.jeancmr.library_management.dto;

import com.jeancmr.library_management.enums.MemberStatus;

public class MemberStatusUpdateRequestDto {
    private MemberStatus status;
    private int borrowLimit;
}
