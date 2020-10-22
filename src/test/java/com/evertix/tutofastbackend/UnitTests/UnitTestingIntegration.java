package com.evertix.tutofastbackend.UnitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AuthenticationUnitTesting.class,
        PlanUnitTesting.class,
        SessionUnitTesting.class
})
public class UnitTestingIntegration {
}
