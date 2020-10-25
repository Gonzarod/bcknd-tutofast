package com.evertix.tutofastbackend.UnitTests;

import com.evertix.tutofastbackend.UnitTests.tests.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AuthenticationUnitTesting.class,
        PlanUnitTesting.class,
        SessionUnitTesting.class,
        CourseUnitTesting.class,
        UserUnitTesting.class,
        ComplaintUnitTesting.class,
        ReviewUnitTesting.class

})
public class UnitTestingIntegration {
}
