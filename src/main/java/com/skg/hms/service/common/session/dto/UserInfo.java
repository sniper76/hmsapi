package com.skg.hms.service.common.session.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class UserInfo implements Serializable {
	private String usrId;
	private String usrNm;
	private String lognId;
	private String clientIp;
}
