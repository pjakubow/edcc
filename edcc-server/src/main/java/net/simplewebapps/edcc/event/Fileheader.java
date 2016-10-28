package net.simplewebapps.edcc.event;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName
public class Fileheader extends Event {

    private Integer part;
    private String language;
    private String gameversion;
    private String build;

    public int getPart() {
        return part;
    }

    public void setPart(int part) {
        this.part = part;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getGameversion() {
        return gameversion;
    }

    public void setGameversion(String gameversion) {
        this.gameversion = gameversion;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    @Override
    public String toString() {
        return "Fileheader{" + "timestamp=" + timestamp +
          ", part=" + part +
          ", language='" + language + '\'' +
          ", gameversion='" + gameversion + '\'' +
          ", build='" + build + '\'' +
          '}';
    }
}
