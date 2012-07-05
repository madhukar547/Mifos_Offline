/*
 * Copyright (c) 2005-2011 Grameen Foundation USA
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * See also http://www.apache.org/licenses/LICENSE-2.0.html for an
 * explanation of the license and how it is applied.
 */

package mifosoffline;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;
//@XmlRootElement(name="addressdto")
public class AddressDto implements Serializable {
	
	public AddressDto(){
		
	}

    private String line1;
    private String line2;
    private String line3;
    private String city;
    private String state;
    private String country;
    private String zip;
    private String phoneNumber;
    private String displayAddress;

    public void setDisplayAddress(String displayAddress) {
		this.displayAddress = displayAddress;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public void setLine3(String line3) {
		this.line3 = line3;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public AddressDto(String line1, String line2, String line3, String city, String state, String country, String zip, String phoneNumber) {
        this.line1 = StringUtils.defaultIfEmpty(line1, "");
        this.line2 = StringUtils.defaultIfEmpty(line2, "");
        this.line3 = StringUtils.defaultIfEmpty(line3, "");
        this.city = StringUtils.defaultIfEmpty(city, "");
        this.state = StringUtils.defaultIfEmpty(state, "");
        this.country = StringUtils.defaultIfEmpty(country, "");
        this.zip = StringUtils.defaultIfEmpty(zip, "");
        this.phoneNumber = StringUtils.defaultIfEmpty(phoneNumber, "");
    }

    public String getLine1() {
        return this.line1;
    }


    public String getLine2() {
        return this.line2;
    }


    public String getLine3() {
        return this.line3;
    }


    public String getCity() {
        return this.city;
    }


    public String getState() {
        return this.state;
    }


    public String getCountry() {
        return this.country;
    }


    public String getZip() {
        return this.zip;
    }


    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getDisplayAddress() {
        return this.displayAddress;
    }
}
