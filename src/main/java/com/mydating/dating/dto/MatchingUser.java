package com.mydating.dating.dto;

import java.util.List;

import com.mydating.dating.util.UserGender;


import lombok.Data;
@Data
public class MatchingUser {
	private int id;
	private String name;
	private String email;
	private long phno;
	private int age;
	private UserGender gender;
	private List<String> interests;
	private int ageDiff;
	private int mic;//matching interests count

}