# A simple makefile for a Hello World Java program

# Define a makefile variable for the java compiler
JCC = javac

# Define a makefile variable for compilation flags
# The -g flag compiles with debugging information
JFLAGS = -g

# typing 'make' will invoke the first target entry in the makefile
# (the default one in this case)
default: $(subst .java,.class,$(wildcard *.java))

# this target entry builds the Average class
# the Average.class file is dependent on the Average.java file
# and the rule associated with this entry gives the command to create it
#
%.class : %.java
	$(JCC) $(JFLAGS) $<

# To start over from scratch, type 'make clean'. 
# Removes all .class files, so that the next make rebuilds them
#
clean:
	$(RM) *.class