package com.varshathakur.cabbooking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Rider {
  String id;
  String name;
  Integer Age;
  String sex;
}
