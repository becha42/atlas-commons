package com.wnagele.atlas.commons.examples;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public abstract class CommandExecutor {
	public void execute(String[] args) {
		try {
			Options options = createCommandlineOptions();
			try {
				CommandLineParser parser = new PosixParser();
				CommandLine cmd = parser.parse(options, args);

				String username = cmd.getOptionValue("username");
				String password = cmd.getOptionValue("password");
				String[] urls = cmd.getOptionValues("url");

				execute(username, password, urls);
			} catch (ParseException e) {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp(getClass().getName(), options);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public abstract void execute(String username, String password, String[] urls) throws Exception;

	@SuppressWarnings("static-access")
	private static Options createCommandlineOptions() {
		Options options = new Options();
		options.addOption(OptionBuilder.hasArg().withArgName("username").isRequired().withDescription("RIPE NCC Access username").create("username"));
		options.addOption(OptionBuilder.hasArg().withArgName("password").isRequired().withDescription("RIPE NCC Access password").create("password"));
		options.addOption(OptionBuilder.hasArgs().withArgName("url").isRequired().withDescription("Data URLs to fetch").create("url"));
		return options;
	}
}