package com.evertix.tutofastbackend.UnitTests;

import com.evertix.tutofastbackend.UnitTests.tests.AuthenticationUnitTesting;
import com.evertix.tutofastbackend.UnitTests.tests.PlanUnitTesting;
import com.evertix.tutofastbackend.UnitTests.tests.SessionUnitTesting;
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
