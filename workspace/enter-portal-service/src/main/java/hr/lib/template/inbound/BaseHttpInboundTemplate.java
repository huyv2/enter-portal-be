package hr.lib.template.inbound;

public abstract class BaseHttpInboundTemplate<T, P> {
	public P postMapping(T requestBody) {
		return null;
	}
	public P getMapping(T requestParam) {
		return null;
	}
}
