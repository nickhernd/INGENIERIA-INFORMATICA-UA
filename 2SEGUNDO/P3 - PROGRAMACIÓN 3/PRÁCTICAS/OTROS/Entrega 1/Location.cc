#include <iomanip>
#include <math.h>
#include "Location.h"
#include "World.h"

const double Location::UPPER_Y_VALUE = 255.0;
const double Location::SEA_LEVEL = 63.0;

ostream& operator<<(ostream& os, const Location& loc) {

    os << "Location{world=" ;
    if (loc.world==NULL)
        os << "NULL";
    else 
        os << *loc.world;

    // para usar mismo formato númerico de salida que en Java. Omitir en Java
    os << fixed << setprecision(1);

    os << ",x=" << loc.x << ",y=" << loc.y << ",z=" << loc.z << "}";

    // instrucciones para restablecer formato numérico por defecto en Java. Omitir en Java
    os.setf(0, ios::floatfield);
    os << setprecision(17);

    return os;
}

Location::Location(World* w, double x, double y, double z) 
{
    world = w;
    setX(x);
    setY(y);
    setZ(z);
}

Location::Location(const Location& loc) 
{
  world = loc.world;
  x = loc.x;
  y = loc.y;
  z = loc.z;
}

Location& 
Location::operator+=(const Location& loc) {
    if (loc.world != world) 
        cerr << "Cannot add Locations of differing worlds."<<endl;
    else {
        x += loc.x;
        setY(y + loc.y);
        z += loc.z;
    }
    return *this;
}
    
Location::~Location() {}

double 
Location::distance(const Location& loc) const { 
    if (loc.getWorld() == NULL || getWorld() == NULL) {
        cerr << "Cannot measure distance to a null world" << endl;
        return -1.0;
    } else if (loc.getWorld() != getWorld()) {
        cerr << "Cannot measure distance between " + world->getName() + " and " + loc.world->getName() << endl;
        return -1.0;
    }
    
    double dx = x - loc.x;
    double dy = y - loc.y;
    double dz = z - loc.z;
    return sqrt(dx*dx + dy*dy + dz*dz);
}

void 
Location::setWorld(World* w) { this->world = w; }
    
void 
Location::setX(double x) { this->x = x; }
    
void 
Location::setY(double y) { 
    
    this->y = (y <= UPPER_Y_VALUE) ? (y >= 0.0 ? y : 0.0) : UPPER_Y_VALUE;
}
    
void 
Location::setZ(double z) { this->z = z; }
    
double 
Location::length() const { return sqrt(x*x + y*y + z*z); }
    
Location& 
Location::operator*=(double factor) { 
  x *= factor;
  setY(y * factor);
  z *= factor;
  return *this;
}

Location& 
Location::operator-=(const Location& loc) { 
    if (loc.world != world) 
        cerr << "Cannot substract Locations of differing worlds."<<endl;
    else {
        x -= loc.x;
        setY(y - loc.y);
        z -= loc.z;
        }    
    return *this;
 }
    
Location& 
Location::zero() { 
    x = y = z = 0.0;
    return *this;
}
    
bool 
Location::operator==(const Location &loc) const { 
      return (x == loc.x && y == loc.y && z == loc.z && world == loc.world);
}
    
int 
Location::hashCode() const { 
		const int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
		result = prime * result + ((world == NULL) ? 0 : world->hashCode());
		return result;
}
