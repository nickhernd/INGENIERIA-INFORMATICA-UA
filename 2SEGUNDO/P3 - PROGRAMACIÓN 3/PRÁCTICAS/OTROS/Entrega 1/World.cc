#include "World.h"


ostream& operator<<(ostream& os, const World& loc) {
    os << loc.name;
    return os;
}

World::World(string name) : name(name) {}

string 
World::getName() {
    return name;
}

bool 
World::operator==(const World & w) const {
    return w.name == this->name;
}

int 
World::hashCode() const {
		const int prime = 31;
		int result = 1;
		result = prime * result + name.length();
		return result;
}
