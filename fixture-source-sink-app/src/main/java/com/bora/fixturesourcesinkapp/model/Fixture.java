package com.bora.fixturesourcesinkapp.model;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="msg")
@XmlAccessorType(XmlAccessType.NONE)
public class Fixture {
    @XmlAttribute(name="id")
    private ID id;
    @XmlAttribute(name="done")
    private String done;

    public Fixture() {
    }

    public Fixture(ID id, String done) {
        this.id = id;
        this.done = done;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }


    public class ID {
        private String value;

        @XmlValue
        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
