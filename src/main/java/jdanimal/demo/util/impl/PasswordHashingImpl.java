package jdanimal.demo.util.impl;

import com.google.common.hash.Hashing;
import jdanimal.demo.util.PasswordHashing;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class PasswordHashingImpl implements PasswordHashing {

    @Override
    public String passwordHashing(String password) {
        return Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }
}
