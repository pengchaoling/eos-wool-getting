package io.bigbearbro.eos4j.client.transaction;

import io.bigbearbro.eos4j.eosio.chain.action.Action;

import java.util.List;

public interface ActionCollector {
	public List<Action> collectActions();
}
