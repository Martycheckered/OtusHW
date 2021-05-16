import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources({"classpath:testConfig.properties"})
public interface TestConfig extends Config {
    @Key("site.url")
    String otusUrl();

    @Key("otus.address")
    String otusAddress();

    @Key("tele2.url")
    String tele2();

    @Key("course.program.answer")
    String answer();

    @Key("subscribe.success")
    String success();

    @Key("ya.market.url")
    String market();

}
