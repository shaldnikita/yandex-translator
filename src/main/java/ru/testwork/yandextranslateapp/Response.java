/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.testwork.yandextranslateapp;

import lombok.Data;

/**
 *
 * @author ShaldNikita
 */
@Data
public class Response {
    private String[] text;

    private String code;

    private String lang;

}
