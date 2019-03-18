package com.example.thymeleafdemo.validation;

//import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
//import static java.lang.annotation.ElementType.FIELD;
//import static java.lang.annotation.ElementType.METHOD;
//
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//
//import javax.validation.Constraint;
//import javax.validation.Payload;
//
//@Target({ METHOD, FIELD, ANNOTATION_TYPE })
//@Retention(RetentionPolicy.RUNTIME)
//@Constraint(validatedBy = ForenameValidator.class)
//public @interface Forename {
//	String message() default "{samples.gae.forename}";
//
//	Class<?>[] groups() default {};
//
//	Class<? extends Payload>[] payload() default {};
//}