package com.sullay.model.temp;

import com.sullay.model.Anthology;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=true)
public class AnthologyTemp extends Anthology {
	  private String passWord;
}
