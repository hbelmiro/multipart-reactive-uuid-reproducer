package org.acme;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@io.quarkus.runtime.annotations.RegisterForReflection
public class Address {

    private String street;
    private String city;

    /**
    * Street name
    * @return street
    **/
    @JsonProperty("street")
    public String getStreet() {
        return street;
    }

    /**
     * Set street
     **/
    public void setStreet(String street) {
        this.street = street;
    }

    public Address street(String street) {
        this.street = street;
        return this;
    }

    /**
    * Name of the city
    * @return city
    **/
    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    /**
     * Set city
     **/
    public void setCity(String city) {
        this.city = city;
    }

    public Address city(String city) {
        this.city = city;
        return this;
    }

    /**
     * Create a string representation of this pojo.
     **/
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Address {\n");

        sb.append("    street: ").append(toIndentedString(street)).append("\n");
        sb.append("    city: ").append(toIndentedString(city)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private static String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}