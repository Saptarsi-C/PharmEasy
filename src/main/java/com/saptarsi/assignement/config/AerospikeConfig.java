/**
 * 
 */
package com.saptarsi.assignement.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.AerospikeException;
import com.aerospike.client.Host;
import com.aerospike.client.policy.ClientPolicy;
import com.aerospike.client.policy.CommitLevel;
import com.aerospike.client.policy.ConsistencyLevel;
import com.aerospike.client.policy.GenerationPolicy;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.WritePolicy;

/**
 * @author saptarsichaurashy
 *
 */
@Configuration
public class AerospikeConfig {

	private final static Logger log = LoggerFactory.getLogger(AerospikeConfig.class);

	@Bean(destroyMethod = "close")
	public AerospikeClient getAerospikeClient(@Value("${aerospike.active}") String aeroSpikeActive,
			@Value("${aerospike.host}") String host, @Value("${aerospike.port}") int port,
			@Value("${aerospike.namespace}") String namespace, @Value("${aerospike.required}") String aerospike) {

		if (aeroSpikeActive == null || aeroSpikeActive.equalsIgnoreCase("false"))
			return null;

		List<String> hostList = Arrays.asList(host.split(","));
		List<Host> hosts = new ArrayList<>();
		hostList.forEach(aerospikeHost -> {
			hosts.add(new Host(aerospikeHost, port));
		});
		// Establish a connection to Aerospike cluster
		AerospikeClient client = null;
		try {
			ClientPolicy cPolicy = new ClientPolicy();
			cPolicy.timeout = 5000;
			client = new AerospikeClient(cPolicy, hosts.toArray(new Host[hosts.size()]));
		} catch (AerospikeException ex) {
			if(aerospike.equalsIgnoreCase("true")){
				throw new RuntimeException("Connection to Cluster Failed");
			}
			log.error("Aerospike client creation failed");
		}
		return client;
	}

	@Bean
	@Qualifier("writePolicy")
	public WritePolicy getWritePolicy(@Value("${aerospike.write.policy}") String policy,
			@Value("${aerospike.write.consistency}") String consistency) {

		WritePolicy writePolicy = new WritePolicy();
		writePolicy.generationPolicy = GenerationPolicy.NONE;
		writePolicy.timeout = 500;
		writePolicy.durableDelete = true;
		writePolicy.commitLevel = CommitLevel.COMMIT_ALL;
		if (policy.equals("create_only")) {
			writePolicy.recordExistsAction = RecordExistsAction.CREATE_ONLY;
		}
		if (consistency.equals("master")) {
			writePolicy.commitLevel = CommitLevel.COMMIT_MASTER;
		}
		return writePolicy;
	}

	@Bean
	@Qualifier("readPolicy")
	public Policy getPolicy(@Value("${aerospike.read.consistency}") String readConsistency) {

		Policy policy = new Policy();
		policy.timeout = 200;
		policy.consistencyLevel = ConsistencyLevel.CONSISTENCY_ONE;
		if (readConsistency.equalsIgnoreCase("full_consistent")) {
			policy.consistencyLevel = ConsistencyLevel.CONSISTENCY_ALL;
		}

		return policy;
	}
}
