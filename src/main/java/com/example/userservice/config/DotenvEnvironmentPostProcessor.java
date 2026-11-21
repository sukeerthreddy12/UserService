package com.example.userservice.config;

import org.springframework.boot.EnvironmentPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Loads a project-root {@code .env} file so {@code ${NAME}} placeholders in
 * application.properties resolve without exporting each variable in the shell.
 * Real OS environment variables still win, because this source is added last.
 */
public class DotenvEnvironmentPostProcessor implements EnvironmentPostProcessor {

	private static final String SOURCE_NAME = "dotenv";

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		Path envFile = Path.of(System.getProperty("user.dir"), ".env");
		if (!Files.isRegularFile(envFile)) {
			return;
		}

		Map<String, Object> values = new LinkedHashMap<>();
		try {
			for (String rawLine : Files.readAllLines(envFile)) {
				String line = rawLine.trim();
				if (line.isEmpty() || line.startsWith("#")) {
					continue;
				}
				if (line.startsWith("export ")) {
					line = line.substring("export ".length()).trim();
				}
				int separator = line.indexOf('=');
				if (separator <= 0) {
					continue;
				}
				String key = line.substring(0, separator).trim();
				String value = unquote(line.substring(separator + 1).trim());
				values.put(key, value);
			}
		} catch (IOException ex) {
			throw new IllegalStateException("Failed to read " + envFile.toAbsolutePath(), ex);
		}

		if (!values.isEmpty()) {
			environment.getPropertySources().addLast(new MapPropertySource(SOURCE_NAME, values));
		}
	}

	private static String unquote(String value) {
		if (value.length() >= 2) {
			char first = value.charAt(0);
			char last = value.charAt(value.length() - 1);
			if ((first == '"' && last == '"') || (first == '\'' && last == '\'')) {
				return value.substring(1, value.length() - 1);
			}
		}
		return value;
	}
}
