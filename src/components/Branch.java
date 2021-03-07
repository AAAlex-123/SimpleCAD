package components;

import static java.lang.Math.abs;
import static java.lang.Math.min;

// A connection between two Components
final class Branch extends Component {

	private final Component in, out;
	private final int indexIn, indexOut;

	Branch(Gate in, int gateIndexIn, Gate out, int gateIndexOut) {
		this.in = in;
		this.out = out;
		indexIn = gateIndexIn;
		indexOut = gateIndexOut;

		in.addOut(this, indexIn);
		out.setIn(this, indexOut);
		wake_up(in.outputPins[indexIn].active, 0);
	}

	Branch(InputPin in, Gate out, int gateIndex) {
		this.in = in;
		this.out = out;
		indexIn = 0;
		indexOut = gateIndex;

		in.addOut(this, indexIn);
		out.setIn(this, indexOut);
		wake_up(in.active, 0);
	}

	Branch(Gate in, OutputPin out, int gateIndex) {
		this.in = in;
		this.out = out;
		indexIn = gateIndex;
		indexOut = 0;

		in.addOut(this, indexIn);
		out.setIn(this, indexOut);
		wake_up(in.outputPins[indexIn].active, 0);
	}

	@Override
	void wake_up(boolean newActive, int index, boolean prevChangeable) {
		checkIndex(index, 1);
		changeable = prevChangeable;

		// propagate signal only if it's different
		if (active != newActive) {
			active = newActive;
			out.wake_up(active, indexOut);
		}
	}

	void disconnect() {
		checkChangeable();
		in.removeOut(this, indexOut);
		out.removeIn(this, indexIn);

		// inform out that there is no longer an input
		out.wake_up(false, indexOut);
	}
}
