package de.javsper.springboottradingibkr.client.aspect;
import de.javsper.springboottradingdata.config.PropertiesConfig;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class ActiveMarketDataAspect {

    private final PropertiesConfig propertiesConfig;

    public ActiveMarketDataAspect(PropertiesConfig propertiesConfig) {
        this.propertiesConfig = propertiesConfig;
    }

    @Before("bean(eClientSocket) && execution(* *(int, ..)) && args(id,..) && execution(* reqMktData(int, ..))")
    protected void addToActiveMarketData( int id){
        propertiesConfig.addToActiveMarketData(id);
    }

//    @Before("bean(eClientSocket) && execution(* *(int, ..)) && args(id,..) && execution(* cancelMktData(int, ..))")
//    protected void removeFromActiveMarketData( int id){
//        propertiesConfig.removeFromActiveMarketData(id);
//    }
}
