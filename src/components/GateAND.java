package components;

class GateAND extends PrimitiveGate {

	public GateAND(int in) {
		super(in, 1);
	}

	@Override
	protected boolean calculateOutput() {
		boolean res = true;

		for (int i = 0; i < innerIn.length; ++i) {
			res &= in[i].getActive();
		}

		return res;
	}
}
