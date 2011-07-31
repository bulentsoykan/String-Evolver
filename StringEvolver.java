//
//  StringEvolver.java
//  
//
//  Created by Roberto Francés on 1/8/10.
//  Copyright 2010 KorovaSoft. All rights reserved.
//
import java.util.Random;
import java.lang.*;
import java.lang.Math.*;
//import korovasoft.StringOrganism;

public class StringEvolver {
	
	public String[] string_pallet = {
	"a", "b", "c", "d", "e", "f", "g", "h", "i" ,"j",
	"k", "l", "m", "n", "o", "p", "q", "r", "s" ,"t",
	"u", "v", "w", "x", "y", "z", " ", "A", "B", "C",
	"D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
	"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
	"X", "Y", "Z", ",", ".", "?", "!"
	};
	public Random generator = new Random();
	public StringBuffer goal_string;
	public boolean continue_simulation;
	public int generation_id = 0;
	public final int num_children = 1000;
	
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("You need to supply a goal string for evolving towards");
		} else {
			
			StringEvolver evolution_engine = new StringEvolver(args[0]);
			evolution_engine.begin_evolution();
		}
	}
	
	StringEvolver(String args) {
		goal_string = new StringBuffer(args);
		continue_simulation = true;
		
		System.out.println(goal_string);
	}
	
	public void begin_evolution() {
		int genome_length = goal_string.length();
		StringOrganism initial_organism = primordial_with_length(genome_length);
		initial_organism.fitness_score = string_metric(initial_organism.genome, goal_string);
		initial_organism.has_parent = false;
		evolution_loop(initial_organism);
	}
	
	public void evolution_loop(StringOrganism parent_organism) {
		while (continue_simulation) {
			display_organism(parent_organism);
			//StringBuffer[] population = make_children(parent_organism);
			parent_organism = rank_population(parent_organism, make_children(parent_organism));
		}
		simulation_end(parent_organism);
	}
	
	public void display_organism(StringOrganism organism) {
		System.out.printf(
			"%d) [%d] %s\n", 
			++generation_id, 
			organism.fitness_score, 
			organism.genome
		);
	}
	
	public StringOrganism rank_population(StringOrganism parent, StringOrganism[] population) {
		int pop_size = population.length;
		StringOrganism best_child = parent;
		int current_high_fitness = string_metric(parent.genome,goal_string);
		
		for (int i = 0; i < pop_size; i++) {
			int current_fitness = string_metric((population[i]).genome,goal_string);
			if (current_fitness < current_high_fitness) {
				best_child = population[i];
				best_child.fitness_score = current_fitness;
				current_high_fitness = current_fitness;
			}
		}
		if (current_high_fitness == 0) {
			continue_simulation = false;
		}
		return best_child;
	}
	
	public StringOrganism[] make_children(StringOrganism initial_organism) {
		StringOrganism[] population = new StringOrganism[num_children];
		int strlen = initial_organism.genome.length();

		for (int child_id = 0; child_id < num_children; child_id++) {
			StringOrganism new_child = new StringOrganism(initial_organism);
			int mutation_index = generator.nextInt(strlen);
			
			new_child.genome.setCharAt(mutation_index, ((random_char()).toCharArray())[0]);
			population[child_id] = new_child;
		}

		return population;
	}
	
	public void simulation_end(StringOrganism final_organism) {
		display_organism(final_organism);
	}
	
	public StringOrganism primordial_with_length(int genome_length) {
		StringOrganism new_organism = new StringOrganism();
		new_organism.genome = new StringBuffer(genome_length);

		for (int i = 0; i < genome_length; i++) {
			new_organism.genome.append(random_char());
		}
		return new_organism;
	}
	
	public int string_metric(StringBuffer x, StringBuffer y) {
		// This assumes x and y are of the same length;
		// Will burn in flames if len(y) < len(x)
		int strlen = x.length();
		int distance = 0;
		
		for (int i = 0; i < strlen; i++) {
			distance += character_difference(x.charAt(i), y.charAt(i));
		}
		
		return distance;
	}
	
	public int character_difference(char x, char y) {
		return Math.abs((int)(x) - (int)(y));
	}
	
	public String random_char() {
		return string_pallet[generator.nextInt(string_pallet.length)];
	}
}
