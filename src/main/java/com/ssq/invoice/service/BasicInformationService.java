package com.ssq.invoice.service;

import org.springframework.stereotype.Service;

@Service
public class BasicInformationService {
    private static String contactInformation = "contact information hahahahahaha";
    private static String organisationInformation = "organisation information hahahahahaha";

    public static String getContactInformation() {
        return contactInformation;
    }

    public static String setContactInformation(String contactInformation) {
        BasicInformationService.contactInformation = contactInformation;
        return BasicInformationService.contactInformation;
    }

    public static String getOrganisationInformation() {
        return organisationInformation;
    }

    public static void setOrganisationInformation(String organisationInformation) {
        BasicInformationService.organisationInformation = organisationInformation;
    }
}
