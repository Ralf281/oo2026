import React, { useEffect, useState } from 'react'

function Shops() {
  const [todos, setTodos] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    fetch("http://localhost:2004/shops/todos")
      .then(res => res.json())
      .then(data => {
        setTodos(data)
      })
      .catch(err => console.log(err))
      .finally(() => setLoading(false))
  }, [])

  if (loading) {
    return <div className="pt-4">Loading...</div>
  }

  return (
    <div className="flex flex-col gap-6 pt-4">
      <h1 className="text-xl font-semibold">Our shops</h1>

      {todos.map(todo => (
        <div key={todo.id} className="border p-3 rounded">
          <div><b>{todo.title}</b></div>
          <div>
            Status: {todo.completed ? "Done" : "Not done"}
          </div>
        </div>
      ))}
    </div>
  )
}

export default Shops