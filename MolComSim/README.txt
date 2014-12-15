0) INTRODUCTION:
	MolComSim is designed to allow the user to run computer simulations of molecular 
	communication between nanomachines in the hopes of discovering the most efficient
	and reliable ways to communicate inside a human body, where electromagnetic radiation
	would create too much waste heat and in areas where neuronal communication is not 
	possible. MolComSim will be distributed as a jar file, with this README.txt file, a 
	build file (builx.xml) and a default parameters file called input0.dat.
	
	The simulation includes two or more nanomachines (at least one of which must
	include a transmitter and one of which must include a transmitter).  Nanomachines 
	may contain transmitters, receivers, or both. Transmitters will send messages to 
	receivers via information molecules.  Receivers may (depending on simulation 
	parameters) send acknowledgment messages back to transmitters via acknowledgment 
	molecules.  Messages are currently represented as simple message numbers.  There must 
	be at least one message to be sent/received.  Molecular movement can occur either 
	randomly, or along a directional microtubule.  All objects and movement for the 
	simulation will exist within a bounding 3-dimensional box, known as the medium.
	There may be randomly distributed noise molecules which obstruct movement for other 
	molecules.  A message is considered completed when the information molecule containing
	that message is received by a receiver (if acknowledgments are not being used) or 
	the acknowledgment molecule for that message is received by a transmitter.  The 
	simulation ends when either the simulation time runs out (as specified by number 
	of steps), or the last message is completed.  Feedback on results of the simulation
	occur both on the display and (if specified by parameters) in an output file.       
	
1) USING MOLCOMSIM:
	A) Place the jar file (MolcomSim.jar) in whatever directory you want to run MolComSim 
		from.  This is the base directory.   
	B) Place the parameters file (input0.dat, by default) in the base directory.  
	C) Modify the parameters file as specified in part 2.
	D) Place the build file (build.xml) in the base directory.
	E) To run MolComSim From a command prompt, at the base directory, type: 
		> java -jar MolComSim.jar <param1> <param2> ... <param n>
		Where the command-line parameters (<param1> etc) are as specified in part 3
	F) Alternatively, if you have Java installed, and you do not wish to use 
		command line parameters, you can run MolComSim from a command prompt at the 
		base directory by typing:
		> ant
	G) As MolComSim runs, you will see output to the screen as specified in part 4.
	H) When MolComSim completes, you can find an output file in the location specified
		by the input parameters (defaults to output.dat in the base directory), with 
		format as specified by part 5.

2) THE PARAMETERS FILE
	The parameters file specifies most of the parameters used for the simulation.
	A) The parameters file should be either in the default location (base directory, file 
		input0.dat) or in a location specified by the command line parameters (see part 3).
	B) Each line of the parameters file will either be ignored (assumed to be blank or a 
		comment), or contain one of the following parameter names at the beginning of the 
		line, followed by white space, followed by parameter value(s) in the specified 
		format.  Note that the parameters file is case-sensitive.  The user can choose to 
		specify any of the parameters in any order.  If a parameter isn't specified, 
		defaults are chosen, as specified below. 
	C)  The following parameter name, value format, and default value triplets are valid:
		a) The size of the X dimension for the medium, which represents a bounding box
			for all activity within the simulation.  Name: mediumDimensionX, format for 
			value: double, default value: 100.0.
		b) The size of the Y dimension for the medium. Name: mediumDimensionY, format 
			for value: double, default value: 100.0.
		c) The size of the z dimension for the medium. Name: mediumDimensionZ, format 
			for value: double, default value: 100.0.
		d) The maximum length of the simulation in steps, where each step represents
			an unspecified unit of time during which a certain amount of molecular
			movement can occur.  Name: maxSimulationStep, format for value: integer,
			default value: 100000
		e) The distance between sender and receiver for simulations with just one of each.
			Note that this must be less than or equal to the size of the X dimension of the 
			medium.  This is only to be used for simulations where the specific locations
			for sender(s) and receiver(s) are not specified (as those will override this 
			value).  If specified, this will place the sender at x = -mediumDimensionX/2,
			y = 0, z = 0, and the receiver at x = mediumDimensionX/2, y = 0, z = 0.
			Name: distSendReceiver, format for value: double, default value: 90.0.
		f) Transmitter position(s) can be specified by using the transmitter position
			parameter repeatedly, with different values as many times as you want to have 
			transmitters.
			Name: transmitterPosition, 
			format: (<double for x-position>, <double for y-position>, <double for z-position)
			default value: (-45.0, 0.0, 0.0)
		g) Receiver position(s) can be specified by using the receiver position
			parameter repeatedly, with different values as many times as you want to have 
			receivers.
			Name: receiverPosition, 
			format: (<double for x-position>, <double for y-position>, <double for z-position)
			default value: (45.0, 0.0, 0.0)
		h) Transmitters are currently considered to be spheres, all of the same radius.  The 
			radius for transmitters can be specified using the transmitter radius parameter.
			Name: transmitterRadius, format: double, default value: 1.0
		i) Receivers are currently considered to be spheres, all of the same radius.  The 
			radius for receivers can be specified using the receiver radius parameter.
			Name: receiverRadius, format: double, default value: 1.0
		j) The number of messages to be used in the simulation can be specified using:
			Name: numMessages, format: integer, default value: 1
		k) If acknowledgments are being used, the time to wait for an acknowledgment 
			message, after an information message has been transmitted, is specified by 
			the retransmit wait time parameter.  If using acknowledgment, after this many 
			steps have passed, the transmitter will resend the message.
			If acknowledgments are not being used the retransmit wait time parameter
			determines how many steps a transmitter will wait for before transmitting
			message n+1 (the next message), after having transmitted message n. 
			Name: retransmitWaitTime, format: integer, default value: 100
		l) If acknowledgments are being used, the user can specify the number of 
			retransmissions of the same message to attempt before giving up until the 
			acknowledgment is received or the simulation ends.  If acknowledgments are
			not being used transmitters will not retransmit the same message and this 
			parameter is meaningless.
			Name: numRetransmissions, format: integer, default value: 1
		m) The degree of random movement for molecules can be specified for each dimension.
			All molecules will use the same (x, y, z) values for these though.  If the value
			specified is n, all molecules using random movement will attempt to move between
			-n/2 and n/2 units in that direction on each step.  
			For movement in the X dimension this is:
			Name: stepLengthX, format: double, default value: 1.0
		n) For movement in the Y dimension this is:
			Name: stepLengthY, format: double, default value: 1.0
 		o) For movement in the Z dimension this is:
			Name: stepLengthZ, format: double, default value: 1.0
  		p) The user can specify how many molecules to send out each time, the radius of the 
  			molecules, and the type of movement for the molecules, for each molecule type.  
  			This is done via a molecule parameter.  Types of molecules that can be
  			INFO, ACK, or NOISE (case-sensitive).  Types of molecular movement that can be
  			specified are: ACTIVE, PASSIVE, or NONE (case-sensitive).  Active movement will 
  			attempt to make use of microtubules when possible.  Passive movement will not.
  			Molecule params can be used 0 or more times for either the different types of
  			molecules, or for the same type but with different parameters.  Movement type 
  			need not be specified at all, in which case whatever default movement type is 
  			set for that type fo molecules will be used, see (3). 
  			Name: moleculeParams, 
  			format (space-separated values): 
  			<number to send out as integer> 
  			<radius as double> 
  			<molecule type as string>
  			<movement type as string>--optional
  			
  			default values: 1 1.0 INFO PASSIVE
  		q) We use two parameters for active propagation (along microtubules).  Rail 
  			velocity specifies the speed molecules travel along microtubules.
  			Name: velRail, format: double, default value: 1.0
  		r) Probability of derailment is used to specify how likely (continuous range 
  			from 0 to 1) a molecule traveling on a microtubule is likely to "fall of" and
  			switch to passive propagation.  This probability is tested at each step.
  			Name: probDrail, format: double (between 0 and 1), default value: 0.0
  		s) The use collisions parameter is specified to cause a molecule that would 
  			otherwise move into a position where it would collide with another molecule 
  			(their radiuses overlap) to trigger a different action.  In the case of molecules 
  			colliding with noise molecules this involves the molecule not moving to the 
  			new position instead, and, if the molecule is on a microtubule, it will be 
  			"knocked off the microtubule", using passive propagation movement.  
  			If the correct parameters are set and acknowledgment and information molecules
  			collide the information molecules may be removed fromt he simulation.
  			Name: useCollisions, format integer (0 = false, 1 = true), default value: 0
  		t) The use acknowledgments parameter specifies whether or not the simulation should
  			use acknowledgments for messages.
  			Name: useAcknowledgements, format integer (0 = false, 1 = true), default value: 0
  		u) Microtubule params is another parameter that can be used zero or more times (once 
  			for each microtubule in the simulation).  Microtubules are represented as 
  			directional tubes, where the position of the center of each end is specified,
  			and the radius is specified.  Active propagation along microtubules will occur
  			in the direction going from the first center-end point to the second center-end 
  			point.
  			Name: microtubuleParams,
  			format (space-separated values):
  			(<x-center-end1 as double>, <y-center-end1 as double>, <z-center-end1 as double>)
  			(<x-center-end2 as double>, <y-center-end2 as double>, <z-center-end2 as double>)
  			<radius as double>
  			default values: none.  No microtubules used.
  		v) Output file to be used can be specified using the parameter followed by a string
  			for path/file-name.  If nothing is specified, or the string is "Off", nothing
  			will be output to file.  If an output file is used it will be created, or 
  			overwritten if already there.  If no path is specified the file will be created
  			in the base directory.
  			Name: outputFile, format: string, default value: none (no output to file by default)
  	  
3) COMMAND LINE ARGUMENTS:
	Currently the command line arguments can only be specified using "java molcomsim x y z".  
	The ant	build file does not accept command line parameters.  The various parameters, and 
	their values, are space-separated, and case-sensitive.  They are all optional, and may 
	be specified in any order, as long as each parameter is followed by its value(s).  All
	parameter names begin with the '-' and end with the ':' characters to distinguish them 
	from the parameter values.  UNlike the paramters file, if an invalid parameter name is 
	used on the command line an illegal argument exception will be generated.  The following 
	are valid command line arguments:
	A) Path/name for parameter file to read in.
		Name: -pfile:, format: <file path/name as string>, default value: input0.dat
	B) Automatic Repeat Request scheme to use.  This determines the way (if any) that 
		acknowledgments will be used.  The first value for this parameter is a two character
		string code for the scheme to use (which can currently only be "sw", for stop-and-wait,
		so the code is skipped for now), followed immediately (no white-space) by the number of 
		information molecules to use each time a message is sent, and the number of 
		acknowledgment molecules to use each time a message is acknowledged.  These must be 
		positive integers (or 0), separated by a comma, and possible white space. The radius 
		for	these molecules will be 1, and they will use the default movement type.
		Note that additional molecule parameters can be specified for these types of molecules
		in the parameters file.
		Name: -arq:, 
		format: <2-char code><# info molecules as int>,<# ack molecules as int>
		default: none (not using automatic repeat request)	
	C) Default movement type.  You can specify multiple default movement type parameters, 
		each one changes the default movement type for a particular type of molecule.  These
		use the same case-sensitive strings to specify type of molecule and type of movement as 
		the parameters file uses.
		Initial default values are as follows:
		ACK ACTIVE
		INFO ACTIVE
		NOISE NONE
		Default values are only used if movement type is not specified (in the params file) 
		or an automatic repeat request scheme is specified on the command line.
		Name: -dmt:, format: <string for molecule type> <string for molecule movement type>,
		default values: as specified above.

4) OUTPUT TO SCREEN:
	A) Whenever a message completes, either because acknowledgments are not being used and 
		the message was received by a receiver, or, if they are, when the acknowledgment is
		received by a transmitter, MolComSim prints the message number, the step number, and 
		the fact the message was completed to the screen.  If the message was the last message
		it will print that as well.  In that case the simulation will end as well.
	B) When the simulation ends, either because the last message completed or the last step 
		was processed, MolComSim will print to the screen that the simulation is ending, and the 
		last step that was processed.  If the last message was completed it will print that as 
		well.  If the last message was not completed it will list the number of messages that
		were completed and the total number of messages. 

5) OUTPUT TO FILE(S):
	Currently identical to output to screen, if an output file was specified in the parameters 
	file and if it could be opened for writing.  Otherwise nothing is output to file. 
		      
	
  			
			 
			
		
		
		
		
		
		