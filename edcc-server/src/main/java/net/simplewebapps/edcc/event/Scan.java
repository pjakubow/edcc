package net.simplewebapps.edcc.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import net.simplewebapps.edcc.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonTypeName
public class Scan extends Event {

    // Planet, Star
    @JsonProperty("BodyName")
    private String name;

    // Planet, Star
    @JsonProperty("DistanceFromArrivalLS")
    private Double distanceFromArrival;

    // Planet
    @JsonProperty("TidalLock")
    private Boolean tidalLocked;

    // Planet
    @JsonProperty("TerraformState")
    private String terraformState;

    // Star
    @JsonProperty("StarType")
    private String starType;

    // Planet
    @JsonProperty("PlanetClass")
    private String planetClass;

    // Planet
    @JsonProperty("Atmosphere")
    private String atmosphere;

    // Planet
    @JsonProperty("Volcanism")
    private String volcanism;

    // Star
    @JsonProperty("StellarMass")
    private Double stellarMass;

    // Planet (?)
    @JsonProperty("MassEM")
    private Double massEM;

    // Star (only?)
    @JsonProperty("Radius")
    private Double radius;

    // Star
    @JsonProperty("AbsoluteMagnitude")
    private Double absoluteMagnitude;

    // Planet
    @JsonProperty("SurfaceGravity")
    private Double surfaceGravity;

    // Planet, Star
    @JsonProperty("SurfaceTemperature")
    private Double surfaceTemperature;

    // Planet
    @JsonProperty("SurfacePressure")
    private Double surfacePressure;

    // Planet
    @JsonProperty("Landable")
    private Boolean landable;

    // Planet
    @JsonProperty("Materials")
    private Map<String, Double> materials = new HashMap<>();

    // Planet, Star
    @JsonProperty("RotationPeriod")
    private Double rotationPeriod;

    @JsonProperty("Age_MY")
    private Double age;

    // Planet, Star
    @JsonProperty("Rings")
    private List<Ring> rings = new ArrayList<>();

    // Orbital Parameters for any Star/Planet/Moon (except main star of single-star system)

    @JsonProperty("SemiMajorAxis")
    private Double semiMajorAxis;

    @JsonProperty("Eccentricity")
    private Double eccentricity;

    @JsonProperty("OrbitalInclination")
    private Double orbitalInclination;

    @JsonProperty("Periapsis")
    private Double periapsis;

    @JsonProperty("OrbitalPeriod")
    private Double orbitalPeriod;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDistanceFromArrival() {
        return distanceFromArrival;
    }

    public void setDistanceFromArrival(Double distanceFromArrival) {
        this.distanceFromArrival = distanceFromArrival;
    }

    public Boolean getTidalLocked() {
        return tidalLocked;
    }

    public void setTidalLocked(Boolean tidalLocked) {
        this.tidalLocked = tidalLocked;
    }

    public String getTerraformState() {
        return terraformState;
    }

    public void setTerraformState(String terraformState) {
        this.terraformState = terraformState;
    }

    public String getStarType() {
        return starType;
    }

    public void setStarType(String starType) {
        this.starType = starType;
    }

    public String getPlanetClass() {
        return planetClass;
    }

    public void setPlanetClass(String planetClass) {
        this.planetClass = planetClass;
    }

    public String getAtmosphere() {
        return atmosphere;
    }

    public void setAtmosphere(String atmosphere) {
        this.atmosphere = atmosphere;
    }

    public String getVolcanism() {
        return volcanism;
    }

    public void setVolcanism(String volcanism) {
        this.volcanism = volcanism;
    }

    public Double getStellarMass() {
        return stellarMass;
    }

    public void setStellarMass(Double stellarMass) {
        this.stellarMass = stellarMass;
    }

    public Double getMassEM() {
        return massEM;
    }

    public void setMassEM(Double massEM) {
        this.massEM = massEM;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public Double getAbsoluteMagnitude() {
        return absoluteMagnitude;
    }

    public void setAbsoluteMagnitude(Double absoluteMagnitude) {
        this.absoluteMagnitude = absoluteMagnitude;
    }

    public Double getSurfaceGravity() {
        return surfaceGravity;
    }

    public void setSurfaceGravity(Double surfaceGravity) {
        this.surfaceGravity = surfaceGravity;
    }

    public Double getSurfaceTemperature() {
        return surfaceTemperature;
    }

    public void setSurfaceTemperature(Double surfaceTemperature) {
        this.surfaceTemperature = surfaceTemperature;
    }

    public Double getSurfacePressure() {
        return surfacePressure;
    }

    public void setSurfacePressure(Double surfacePressure) {
        this.surfacePressure = surfacePressure;
    }

    public Boolean getLandable() {
        return landable;
    }

    public void setLandable(Boolean landable) {
        this.landable = landable;
    }

    public Map<String, Double> getMaterials() {
        return materials;
    }

    public void setMaterials(Map<String, Double> materials) {
        this.materials = materials;
    }

    public Double getRotationPeriod() {
        return rotationPeriod;
    }

    public void setRotationPeriod(Double rotationPeriod) {
        this.rotationPeriod = rotationPeriod;
    }

    public Double getAge() {
        return age;
    }

    public void setAge(Double age) {
        this.age = age;
    }

    public List<Ring> getRings() {
        return rings;
    }

    public void setRings(List<Ring> rings) {
        this.rings = rings;
    }

    public Double getSemiMajorAxis() {
        return semiMajorAxis;
    }

    public void setSemiMajorAxis(Double semiMajorAxis) {
        this.semiMajorAxis = semiMajorAxis;
    }

    public Double getEccentricity() {
        return eccentricity;
    }

    public void setEccentricity(Double eccentricity) {
        this.eccentricity = eccentricity;
    }

    public Double getOrbitalInclination() {
        return orbitalInclination;
    }

    public void setOrbitalInclination(Double orbitalInclination) {
        this.orbitalInclination = orbitalInclination;
    }

    public Double getPeriapsis() {
        return periapsis;
    }

    public void setPeriapsis(Double periapsis) {
        this.periapsis = periapsis;
    }

    public Double getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public void setOrbitalPeriod(Double orbitalPeriod) {
        this.orbitalPeriod = orbitalPeriod;
    }

    public String toFormattedString() {
        return "Scan{" +
                "\nname='" + name + '\'' +
                ", \ndistanceFromArrival=" + distanceFromArrival +
                ", \ntidalLocked=" + tidalLocked +
                ", \nterraformState='" + terraformState + '\'' +
                ", \nstarType='" + starType + '\'' +
                ", \nplanetClass='" + planetClass + '\'' +
                ", \natmosphere='" + atmosphere + '\'' +
                ", \nvolcanism='" + volcanism + '\'' +
                ", \nstellarMass=" + stellarMass +
                ", \nmassEM=" + massEM +
                ", \nradius=" + radius +
                ", \nabsoluteMagnitude=" + absoluteMagnitude +
                ", \nsurfaceGravity=" + surfaceGravity +
                ", \nsurfaceTemperature=" + surfaceTemperature +
                ", \nsurfacePressure=" + surfacePressure +
                ", \nlandable=" + landable +
                ", \nmaterials=" + materials +
                ", \nrotationPeriod=" + rotationPeriod +
                ", \nage=" + age +
                ", \nrings=" + rings +
                ", \nsemiMajorAxis=" + semiMajorAxis +
                ", \neccentricity=" + eccentricity +
                ", \norbitalInclination=" + orbitalInclination +
                ", \nperiapsis=" + periapsis +
                ", \norbitalPeriod=" + orbitalPeriod +
                '}';
    }

    @Override
    public String toString() {
        return "Scan{" +
                "name='" + name + '\'' +
                ", distanceFromArrival=" + distanceFromArrival +
                ", tidalLocked=" + tidalLocked +
                ", terraformState='" + terraformState + '\'' +
                ", starType='" + starType + '\'' +
                ", planetClass='" + planetClass + '\'' +
                ", atmosphere='" + atmosphere + '\'' +
                ", volcanism='" + volcanism + '\'' +
                ", stellarMass=" + stellarMass +
                ", massEM=" + massEM +
                ", radius=" + radius +
                ", absoluteMagnitude=" + absoluteMagnitude +
                ", surfaceGravity=" + surfaceGravity +
                ", surfaceTemperature=" + surfaceTemperature +
                ", surfacePressure=" + surfacePressure +
                ", landable=" + landable +
                ", materials=" + materials +
                ", rotationPeriod=" + rotationPeriod +
                ", age=" + age +
                ", rings=" + rings +
                ", semiMajorAxis=" + semiMajorAxis +
                ", eccentricity=" + eccentricity +
                ", orbitalInclination=" + orbitalInclination +
                ", periapsis=" + periapsis +
                ", orbitalPeriod=" + orbitalPeriod +
                '}';
    }
}
