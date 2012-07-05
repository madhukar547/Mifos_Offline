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
@XmlRootElement(name="customfielddto")
public class CustomFieldDto implements Serializable {

    private Short fieldId;
    private String fieldValue;
    private Short fieldType;
    private boolean mandatory;
    private String lookUpEntityType;
    private String mandatoryString;
    private String label;

    public CustomFieldDto() {
    }

    public CustomFieldDto(Short fieldId, String fieldValue, Short fieldType) {
        this.fieldId = fieldId;
        this.fieldValue = fieldValue;
        this.fieldType = fieldType;
    }

    public CustomFieldDto(Short fieldId, String fieldValue) {
        this.fieldId = fieldId;
        this.fieldValue = fieldValue;
    }

    public Short getFieldId() {
        return fieldId;
    }

    public void setFieldId(Short fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public Short getFieldType() {
        return fieldType;
    }

    public void setFieldType(Short fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public int hashCode() {
        return fieldId == null ? 0 : fieldId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CustomFieldDto other = (CustomFieldDto) obj;
        if (fieldId == null) {
            if (other.fieldId != null) {
                return false;
            }
        } else if (!fieldId.equals(other.fieldId)) {
            return false;
        }
        return true;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public boolean isMandatory() {
        return this.mandatory;
    }

    public String getLookUpEntityType() {
        return this.lookUpEntityType;
    }

    public void setLookUpEntityType(String lookUpEntityType) {
        this.lookUpEntityType = lookUpEntityType;
    }

    public String getMandatoryString() {
        return this.mandatoryString;
    }

    public void setMandatoryString(String mandatoryString) {
        this.mandatoryString = mandatoryString;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}