0) INTRODUCTION:
	MolComSim is designed to allow the user to run computer simulations of molecular 
	communication between nanomachines in the hopes of discovering the most efficient
	and reliable ways to communicate inside a human body, where electromagnetic radiation
	would create too much waste heat and in areas where neuronal communication is not 
	possible. MolComSim will be distributed as a jar file, with this README.txt file, a 
	build file (build.xml) and a default parameters file called input0.dat.
	
	The simulation includes two or more nanomachines (at least one of which must
	be a transmitter and a different one must be a receiver).  Nanomachines 
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
			value: int, default value: 100.
		b) The size of the Y dimension for the medium. Name: mediumDimensionY, format 
			for value: int, default value: 100.
		c) The size of the z dimension for the medium. Name: mediumDimensionZ, format 
			for value: int, default value: 100.
		d) The maximum length of the simulation in steps, where each step represents
			an unspecified unit of time during which a certain amount of molecular
			movement can occur.  Name: maxSimulationStep, format for value: integer,
			default value: 100,000
		e) Transmitter(s) can be specified by using the transmitter 
			parameter repeatedly, with different values as many times as you want to have 
			transmitters.
			Name: transmitter, 
			format: <center-position> <int for "radius"> <molecule-release-position>
			Positions are of the form (<int for x-position>, <int for y-position>, <int for z-position>)
			nanomachines are cubes, and "radius" is half the length of a side, including the center position.
			radius is optional, and defaults to 1, molecule-release-position (where molecules are released to)
			is optional, and defaults to center-position.
		f) Receiver(s) can be specified by using the receiver
			parameter repeatedly, with different values as many times as you want to have 
			receivers.
			Name: receiver, 
			format and defaults: same as transmitter.
		g) intermediate node(s), or transceiver(s) can be specified by using the intermediateNode
			parameter repeatedly, with different values as many times as you want intermediate nodes.
			Intermediate nodes can act as signal boosters, or connectors between microtubules who's ends
			do not touch.  intermediate nodes can receive both information molecules and acknowledgement molecules.
			When they do, if it is the first time that type of molecule with that message id was received by that microtubule,
			then it will release molecules of that type and with that message id as specified in the param files 
			molecule params.  If it was not the first time the molecule will not be received and no new molecules of that type and
			message id will be produced.  Unlike transmitters and receivers, intermediate nodes contain two molecule release positions,
			one for information molecules and the other for acknowledgement molecules.  By making the intermediate node of sufficient
			size and specifying the molecule release positions correctly, the intermediate node can bridge a gap between microtubules.
			Also, if the number of molecules to be released at once is more then one, the intermediate node will act as a signal
			booster because when it receives one molecule it will send out more then one molecule of the same type and with the same 
			message id.  
			name: intermediateNode
			format: <center-position> <int for "radius"> <information-molecule-release-position> <acknowledgement-molecule-release-position>
			defaults: radius is optional and defaults to 1.  the molecule release positions are also optional, and 
			default to the center position, but if one is specified, both must be specified.			
		h) The number of messages to be used in the simulation can be specified using:
			Name: numMessages, format: integer, default value: 1
		i) If acknowledgments are being used, the time to wait for an acknowledgment 
			message, after an information message has been transmitted, is specified by 
			the retransmit wait time parameter.  If using acknowledgments, after this many 
			steps have passed, the transmitter will resend the message.
			If acknowledgments are not being used the retransmit wait time parameter
			determines how many steps a transmitter will wait for before transmitting
			message n+1 (the next message), after having transmitted message n. 
			Name: retransmitWaitTime, format: integer, default value: 100
		j) If acknowledgments are being used, the user can specify the number of 
			retransmissions of the same message to attempt before giving up until the 
			acknowledgment is received or the simulation ends.  If acknowledgments are
			not being used transmitters will not retransmit the same message and this 
			parameter is meaningless.
			Name: numRetransmissions, format: integer, default value: 0
		k) The degree of random movement for molecules can be specified for each dimension.
			All molecules will use the same (x, y, z) values for these though.  If the value
			specified is n, all molecules using random movement will attempt to move between
			-n and +n units in that direction on each step.  
			For movement in the X dimension this is:
			Name: stepLengthX, format: int, default value: 1
		l) For movement in the Y dimension this is:
			Name: stepLengthY, format: int, default value: 1
 		m) For movement in the Z dimension this is:
			Name: stepLengthZ, format: int, default value: 1
  		n) The user can specify how many molecules to send out each time, 
  			the type of movement for the molecules, and the amount of adaptive change for 
  			each molecule type.This is done via a molecule parameter.  Types of molecules can be
  			INFO, ACK, or NOISE (case-sensitive).  Types of molecular movement that can be
  			specified are: ACTIVE, PASSIVE, or NONE (case-sensitive).  Active movement will 
  			attempt to make use of microtubules when possible.  Passive movement will not.
  			Molecule params can be used 0 or more times for either the different types of
  			molecules, or for the same type but with different parameters.  Movement type 
  			need not be specified at all, in which case whatever default movement type is 
  			set for that type of molecules will be used, see (3). Adaptive change is the 
  			amount to change the number of molecules to send out based on communication success
  			or failure.  Every time communication was a success (new molecule is being sent out
  			because an appropriate molecule of the opposite type with the correct message ID
  			was just received) the value for number of molecules to send out is
  			permanently reduced by the value of adaptive change this group of molecules,
  			but number of molecules to send out cannot go below 1.  Every time new molecules are 
  			sent out when it is not the result of a successful communication the number of molecules
  			of that type to release is permanently increased by the adaptive change number.
  			Not that the adaptive change may be different for different groups of molecules, even of the 
  			same type.    Molecules are 1x1x1 blocks in the simulation.  
  			Name: moleculeParams, 
  			format (space-separated values): 
  			<number to send out as integer> 
  			<molecule type as string>
  			<movement type as string>--optional (default as specified in (3))
  			<adaptive change value as int>-- optional (default = 0)
  			
  			default values: 1 INFO ACTIVE 0
  							1 ACK ACTIVE 0
  		o) We use two parameters for active propagation (along microtubules).  Rail 
  			velocity specifies the speed molecules travel along microtubules.
  			Name: velRail, format: int, default value: 1
  		p) Probability of derailment is used to specify how likely (continuous range 
  			from 0 to 1) a molecule traveling on a microtubule is likely to "fall of" and
  			switch to passive propagation.  This probability is tested at each step.
  			Name: probDrail, format: double (between 0 and 1), default value: 0.0
  		q) The use collisions parameter is specified to cause a molecule that would 
  			otherwise move into a position where it would collide with another molecule 
  			to trigger a different action.  In the case of molecules 
  			colliding with noise molecules this involves the molecule not moving to the 
  			new position instead, and, if the molecule is on a microtubule, it will be 
  			"knocked off the microtubule", using diffusive random movement during the next step.  
  			If the correct parameters are set and acknowledgement and information molecules
  			collide the information molecules may be removed from the simulation.
  			Name: useCollisions, format integer (0 = false, 1 = true), default value: 1
  		r) The use acknowledgments parameter specifies whether or not the simulation should
  			use acknowledgments for messages.
  			Name: useAcknowledgements, format integer (0 = false, 1 = true), default value: 1
  		s) Microtubule params is another parameter that can be used zero or more times (once 
  			for each microtubule in the simulation).  Microtubules are represented as 
  			directional tubes, where the position of each end is specified,
  			and the microtubule represents a 2x2x length block where length goes from the first
  			position to the second.  Active propagation along microtubules will occur
  			in the direction going from the first (start) point to the second (end) 
  			point.
  			Name: microtubuleParams,
  			format (space-separated values):
  			(<x-start as int>, <y-start as int>, <z-start as int>)
  			(<x-end as int>, <y-end as int>, <z-end as int>)
  			default values: none.  No microtubules used by default.
  		t) Output file to be used can be specified using the parameter followed by a string
  			for path/file-name.  If nothing is specified, or the string is "Off", nothing
  			will be output to file.  If an output file is used it will be created, or 
  			overwritten if already there.  If no path is specified the file will be created
  			in the base directory.
  			Name: outputFile, format: string, default value: none (no output to file by default)
  		u) There is an option that, when there is a collision between an acknowledgement molecule 
  			and an information molecule with the same message id the acknowledgement molecule
  			can decompose the information molecule, removing it from the simulation.  This is 
  			settable using the decomposing parameter, which can be set to 1 (true) or any other 
  			value (false) it is a boolean that defaults to false.  THis is only settable for the 
  			simulation as a whole.
  	  
3) COMMAND LINE ARGUMENTS:
	Currently the command line arguments can only be specified using "java -jar MolComSim.jar x y z".  
	The ant	build file is not configured to accept command line parameters.  The various parameters, and 
	their values, are space-separated, and case-sensitive.  They are all optional, and may 
	be specified in any order, as long as each parameter is followed by its value(s).  All
	parameter names begin with the '-' and end with the ':' characters to distinguish them 
	from the parameter values (except for batchRun which does not use a ':').  
	Unlike the parameters file, if an invalid parameter name is 
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
	D) batchRun.  Unlike the other parameters, this does not require a colon after it, as it has no 
		value.  If it is used, that means the current run is a batch run for MolComSim, and MolComSim will 
		provide extremely minimal output.  It will only report the last step the simulation reached, and
		will not output to the output file, but, instead, will prepend the string "batch_" to 
		the current output file name and append the last step number on a separate line to that file,
		creating it if it exists.  This is intended to be used when MolComSim is run multiple times
		in parallel with the same params file.  If no output file was specified then it will not append to 
		any file.
		Name: -batchRun
		default: not on by default.    

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
		      
6) In addition to MolComSim there is a small utility designed to work with one or more batch files to create 
	whose format is more easily usable by Excel for importing data for sorting or graphs.  This utility is part of 
	the MolComSim project, but in a separate package (batchfilecollator).  It gets compiled with everything else,
	but to run it from ant you should specify run-collator as the target.  The collator will look in the current 
	directory for files starting with the name "batch_" and, for each such file, will read it line by line, and append a
	line to the file collated.txt (creating this if necessary), in the current directory.  The new line starts with the 
	name of the batch file, followed by a space, and then contains each value in the batch file, followed by a space, and 
	ending with a newline character.  If several batch files exist, each will be on its own line, making it clear
	from the batch file name what data belonged to what batch run when importing to Excel. 
	The jar file generated for the collator is in the same directory as MolComSim.jar.
	
7) There is also a small Windows utility to facilitate batch runs of as many as 100 MolComSim runs (10 at a time, 10 times in a row).
	This is called batchrun.cmd, and is in the main MolComSim directory.  It will run MolComSim asynchronously, in batch mode, but
	there are problems if it runs MolComSim more then about 10 times in parallel.  In order to prevent this issue, the cmd file
	has a small built-in delay before starting the next set of ten runs in parallel: "timeout /t 10 /nobreak".  Here the 10 
	represents 10 seconds that it will wait before starting the next batch of 10 runs.  This should be varied based on the parameters
	used to run MolComSim so that the ten runs of MolComSim never finish later then this number of seconds, but also do not
	finish much earlier.  You can see how long this takes from the number of steps reported to output when each batch run terminates, 
	and the number of seconds elapsed is shown as a countdown as well.
	
8)  The current sources are in professor Suki's dropbox directory under the "umass_project/currentsources and eclipse project" directory.  
	The application was developed in Java in Eclipse, and shared using github.  

9) The design documents are in professor Suzuki's dropbox directory under the "umass_project/new design" directory.  The primary
	files of interest would probably be the UML class code in MComSim2.ncp.  This file requires the freeware program NClass,
	available here: http://nclass.sourceforge.net/
	to properly use the file, but to just see the class diagrams you can look through "MolComSim New Design.pptx".  
	Note that the .pptx is somewhat out of date, and the .ncp file is also slightly out of date, although mostly accurate.
	There is also pseudocode for the new design (also somewhat out of date) in "MolComSim Design.docx".
	
10) If you have any further questions, feel free to contact Jon Mitzman (j.mitzman 'at' comcast 'dot' net), 
	Bria Morgan (Bria.Morgan001 'at' umb 'dot' edu), or T. Omar Soro (tsoros 'at' gmail 'dot' com). 
  			
			 
			
		
		
		
		
		
		