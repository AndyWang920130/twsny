package com.tswny.init.service.mapper;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Named("CustomFormatter")
public class CustomFormatter {

    @Named("ListToString")
    public static String ListToString(List<String> items) {
        return items != null ? String.join("; ", items) : null;
    }

    @Named("StringToList")
    public static List<String> StringToList(String items) {
        if (StringUtils.isBlank(items)) return new ArrayList<>();
        String[] itemArray = items.split(";");
        return Arrays.asList(itemArray);
    }
}
