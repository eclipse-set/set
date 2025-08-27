import { Coordinate } from '@/model/Position'
import { distance } from './Math'

export class Coord {
  static EPSILON_DISTANCE_ZERO = 0.000001

  public static distance (a: Coordinate, b:Coordinate): number {
    return distance([a.x, a.y], [b.x, b.y])
  }

  public static normalizedDirection (a:Coord,b:Coord): Coord | undefined {
    const len = Coord.distance(a,b)
    if (len < Coord.EPSILON_DISTANCE_ZERO ) {
      return undefined
    }

    return new Coord((b.x - a.x) / len,(b.y - a.y) / len)
  }

  public exact_eq (other:Coordinate): boolean {
    return this.x === other.x && this.y === other.y
  }

  public scaledBy (scalar: number): Coord{
    return new Coord(this.x * scalar, this.y * scalar)
  }

  public plus (other: Coord): Coord{
    return new Coord(this.x + other.x, this.y + other.y)
  }

  public toOlPoint () {
    return new OlPoint([
      this.x,
      this.y
    ])
  }
}
