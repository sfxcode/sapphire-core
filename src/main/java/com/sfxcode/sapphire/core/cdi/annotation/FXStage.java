package com.sfxcode.sapphire.core.cdi.annotation;

import java.lang.annotation.*;

import javax.inject.Qualifier;

@Qualifier
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FXStage {
}
