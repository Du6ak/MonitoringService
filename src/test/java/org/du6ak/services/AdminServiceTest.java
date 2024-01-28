package org.du6ak.services;

import org.du6ak.models.Log;
import org.du6ak.models.User;
import org.junit.Test;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;

import java.util.Queue;

class AdminServiceTest {

    @Test
    public static void getUserLog() throws Exception {
        Queue<Log> userLog = AdminService.getUserLog(new User("test", "test", false));
//        Assertions.assertEquals(, userLog);

    }
}
