import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources({"classpath:testConfig.properties"})
public interface TestConfig extends Config {
    @Key("site.url")
    String OtusUrl();

}
